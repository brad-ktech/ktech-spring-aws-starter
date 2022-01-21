package com.ktech.starter.utilities;

import java.io.File;

public abstract class FileObserver extends Thread {

	/**
	 * The default delay between every file modification check, set to 60 seconds.
	 */
	static final public long DEFAULT_DELAY = 60000;
	/**
	 * The name of the file to observe for changes.
	 */
	protected String filename;

	/**
	 * The delay to observe between every check. By default set
	 * {@link #DEFAULT_DELAY}.
	 */
	protected long delay = DEFAULT_DELAY;

	private File file;
	private long lastModified = 0;
	private boolean warnedAlready = false;
	private boolean interrupted = false;

	protected FileObserver(String filename) {
		super("FileObserver");
		this.filename = filename;
		file = new File(filename);
		setDaemon(true);
		checkAndConfigure();
	}

	/**
	 * Set the delay to observe between each check of the file changes.
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	abstract protected void doOnChange();

	protected void checkAndConfigure() {
		boolean fileExists;
		try {
			fileExists = file.exists();
		} catch (SecurityException e) {

			interrupted = true;
			return;
		}

		if (fileExists) {
			long l = file.lastModified();
			if (l > lastModified) {
				lastModified = l;
				doOnChange();
				warnedAlready = false;
			}
		} else {
			if (!warnedAlready) {

				warnedAlready = true;
			}
		}
	}

	public void run() {
		while (!interrupted) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				interrupted = true;
				break;
			}
			checkAndConfigure();
		}
	}
}