/**
 * 
 */
package com.ktech.starter.utilities;

import com.ktech.starter.exceptions.CloneException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;





/**
 * @author bjestis
 *
 */
public class Cloner {

	@SuppressWarnings("unchecked")
	public static <T> T serializedClone(T source) throws CloneException {

		T ret = null;
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(bos)) {

			out.writeObject(source);

			try (ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
					ObjectInputStream in = new ObjectInputStream(bin)) {
				ret = (T) in.readObject();
			}

		}
		catch (IOException | ClassNotFoundException e) {
			throw new CloneException("Problem copying target to source: " + source, e);
		}

		return ret;
	}

}
