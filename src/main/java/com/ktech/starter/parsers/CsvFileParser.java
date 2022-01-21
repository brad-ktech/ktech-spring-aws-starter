package com.ktech.starter.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import com.ktech.starter.annotations.CsvIndex;
import com.ktech.starter.annotations.PostParse;
import com.ktech.starter.utilities.Reflectotron;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;






public class CsvFileParser<T> {

    private Class<T> clazz;
    private File file;
    private String delimiter = ",";
    private boolean skipHeader = false;
    private boolean skipFooter = false;

    private CsvFileParser() {

    }


    private CsvFileParser(File file){

        this.file = file;
    }

    public CsvFileParser(String path){
        file = Paths.get(path).toFile();

    }

    public static CsvFileParser withFile(File file){
        return new CsvFileParser(file);
    }

    public static CsvFileParser withPath(Path path){
        return new CsvFileParser(path.toFile());
    }

    public static CsvFileParser withPath(String path){
        return new CsvFileParser(Paths.get(path).toFile());
    }

    public CsvFileParser intoClass(Class<T> clazz){
        this.clazz = clazz;
        return this;
    }

    public CsvFileParser withDelimiter(String delimiter){
        this.delimiter = delimiter;
        return this;
    }

    public CsvFileParser skipHeader(){
        this.skipHeader = true;
        return this;
    }

    public CsvFileParser skipFooter(){
        this.skipFooter = true;
        return this;
    }

    public <T> List<T> parse()	throws FileNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {

        List<T> results = new ArrayList<>();
        CsvParserSettings settings = new CsvParserSettings();
        if (delimiter != null && !delimiter.isEmpty()) {
            settings.getFormat().setDelimiter(delimiter.charAt(0));
        } else {
            settings.getFormat().setLineSeparator("\n");
        }

        CsvParser parser = new CsvParser(settings);
        List<String[]> rows = parser.parseAll(new FileReader(file));

        int rowCount = 0;
        for (String[] row : rows) {
            if (++rowCount == 1 && skipHeader) {
                continue;
            }

            T entity = (T) clazz.newInstance();
            List<Field> fields = Reflectotron.getFieldsWithAnnotation(clazz, CsvIndex.class);
            for (Field field : fields) {
                CsvIndex idx = field.getAnnotation(CsvIndex.class);
                String value = row[idx.index()];
                // field must have a string constructor
                field.setAccessible(true);
				/*if (idx.converter().isAssignableFrom(FieldConverter.class)) {
					FieldConverter converter = (FieldConverter) idx.converter().newInstance();
					field.set(entity, field.getType().cast(converter.convert(value)));
				} else {*/
                // if there is no converter then just use the string constructor
                field.set(entity, value);
//				}

                field.setAccessible(false);

            }

            postParse(entity);
            results.add(entity);

        }

        return results;

    }

    private <T> void postParse(T entity) {

        List<Method> methods = Reflectotron.getMethodsWithAnnotation(entity.getClass(), PostParse.class);
        for (Method method : methods) {
            try {
                method.invoke(entity, null);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}

