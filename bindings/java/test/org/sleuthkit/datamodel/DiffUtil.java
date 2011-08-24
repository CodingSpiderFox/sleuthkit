/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.datamodel;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author pmartel
 */
public class DiffUtil {

	/**
	 * Creates the Sleuth Kit database for an image, generates a string
	 * representation of the resulting database to use as a standard for
	 * comparison, and saves the the standard to a file.
	 * @param standardPath The path to save the standard file to (will be
	 * overwritten if it already exists)
	 * @param tempDirPath An existing directory to create the test database in
	 * @param imagePaths The path(s) to the image file(s)
	 */
	public static void createStandard(String standardPath, String tempDirPath, List<String> imagePaths) {
		java.io.File standardFile = new java.io.File(standardPath);
		try {
			java.io.File firstImageFile = new java.io.File(imagePaths.get(0));
			java.io.File tempDir = new java.io.File(tempDirPath);
			String dbPath = tempDir.getPath() + java.io.File.separator + firstImageFile.getName() + ".db";
			java.io.File dbFile = new java.io.File(dbPath);

			String imageDirPath = firstImageFile.getParent();


			standardFile.createNewFile();
			FileWriter standardWriter = new FileWriter(standardFile);
			ReprDataModel repr = new ReprDataModel(standardWriter);

			dbFile.delete();

			Sleuthkit.makeDb(imagePaths.toArray(new String[imagePaths.size()]), tempDirPath);
			Sleuthkit sk = new Sleuthkit(dbPath, imageDirPath);
			repr.start(sk.getImage());
			standardWriter.close();

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Calls {@link #createStandard(String, String, String[]) createStandard}
	 * with default arguments
	 * @param args Ignored 
	 */
	public static void main(String[] args) {
		String tempDirPath = System.getProperty("java.io.tmpdir");
		List<List<String>> imagePaths = DiffTest.getImagePaths();
		for(List<String> paths : imagePaths) {
			String standardPath = DiffTest.standardPath(paths);
			createStandard(standardPath, tempDirPath, paths);
		}

	}

	private static List<String> fileToLines(String filename) {
		List<String> lines = new LinkedList<String>();
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(new java.io.File(filename).getAbsolutePath()));
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return lines;
	}

	/**
	 * Returns the diff between the two given files
	 * @param pathOriginal The path to the original file
	 * @param pathRevised The path to the revised (new) file
	 * @return A representation of the diff
	 */
	public static String getDiff(String pathOriginal, String pathRevised) {
		List<String> originalLines, revisedLines;
		originalLines = fileToLines(pathOriginal);
		revisedLines = fileToLines(pathRevised);

		// Compute diff. Get the Patch object. Patch is the container for computed deltas.
		Patch patch = DiffUtils.diff(originalLines, revisedLines);
		StringBuilder diff = new StringBuilder();

		for (Delta delta : patch.getDeltas()) {
			diff.append(delta.toString());
			diff.append("\n");
		}

		return diff.toString();
	}
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.datamodel;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author pmartel
 */
public class DiffUtil {

	/**
	 * Creates the Sleuth Kit database for an image, generates a string
	 * representation of the resulting database to use as a standard for
	 * comparison, and saves the the standard to a file.
	 * @param standardPath The path to save the standard file to (will be
	 * overwritten if it already exists)
	 * @param tempDirPath An existing directory to create the test database in
	 * @param imagePaths The path(s) to the image file(s)
	 */
	public static void createStandard(String standardPath, String tempDirPath, List<String> imagePaths) {
		java.io.File standardFile = new java.io.File(standardPath);
		try {
			java.io.File firstImageFile = new java.io.File(imagePaths.get(0));
			java.io.File tempDir = new java.io.File(tempDirPath);
			String dbPath = tempDir.getPath() + java.io.File.separator + firstImageFile.getName() + ".db";
			java.io.File dbFile = new java.io.File(dbPath);

			String imageDirPath = firstImageFile.getParent();


			standardFile.createNewFile();
			FileWriter standardWriter = new FileWriter(standardFile);
			ReprDataModel repr = new ReprDataModel(standardWriter);

			dbFile.delete();

			Sleuthkit.makeDb(imagePaths.toArray(new String[imagePaths.size()]), tempDirPath);
			Sleuthkit sk = new Sleuthkit(dbPath, imageDirPath);
			repr.start(sk.getImage());
			standardWriter.close();

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Calls {@link #createStandard(String, String, String[]) createStandard}
	 * with default arguments
	 * @param args Ignored 
	 */
	public static void main(String[] args) {
		String tempDirPath = System.getProperty("java.io.tmpdir");
		List<List<String>> imagePaths = DiffTest.getImagePaths();
		for(List<String> paths : imagePaths) {
			String standardPath = DiffTest.standardPath(paths);
			createStandard(standardPath, tempDirPath, paths);
		}

	}

	private static List<String> fileToLines(String filename) {
		List<String> lines = new LinkedList<String>();
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(new java.io.File(filename).getAbsolutePath()));
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return lines;
	}

	/**
	 * Returns the diff between the two given files
	 * @param pathOriginal The path to the original file
	 * @param pathRevised The path to the revised (new) file
	 * @return A representation of the diff
	 */
	public static String getDiff(String pathOriginal, String pathRevised) {
		List<String> originalLines, revisedLines;
		originalLines = fileToLines(pathOriginal);
		revisedLines = fileToLines(pathRevised);

		// Compute diff. Get the Patch object. Patch is the container for computed deltas.
		Patch patch = DiffUtils.diff(originalLines, revisedLines);
		StringBuilder diff = new StringBuilder();

		for (Delta delta : patch.getDeltas()) {
			diff.append(delta.toString());
			diff.append("\n");
		}

		return diff.toString();
	}
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sleuthkit.datamodel;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author pmartel
 */
public class DiffUtil {

	/**
	 * Creates the Sleuth Kit database for an image, generates a string
	 * representation of the resulting database to use as a standard for
	 * comparison, and saves the the standard to a file.
	 * @param standardPath The path to save the standard file to (will be
	 * overwritten if it already exists)
	 * @param tempDirPath An existing directory to create the test database in
	 * @param imagePaths The path(s) to the image file(s)
	 */
	public static void createStandard(String standardPath, String tempDirPath, List<String> imagePaths) {
		java.io.File standardFile = new java.io.File(standardPath);
		try {
			java.io.File firstImageFile = new java.io.File(imagePaths.get(0));
			java.io.File tempDir = new java.io.File(tempDirPath);
			String dbPath = tempDir.getPath() + java.io.File.separator + firstImageFile.getName() + ".db";
			java.io.File dbFile = new java.io.File(dbPath);

			String imageDirPath = firstImageFile.getParent();


			standardFile.createNewFile();
			FileWriter standardWriter = new FileWriter(standardFile);
			ReprDataModel repr = new ReprDataModel(standardWriter);

			dbFile.delete();

			Sleuthkit.makeDb(imagePaths.toArray(new String[imagePaths.size()]), tempDirPath);
			Sleuthkit sk = new Sleuthkit(dbPath, imageDirPath);
			repr.start(sk.getImage());
			standardWriter.close();

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Calls {@link #createStandard(String, String, String[]) createStandard}
	 * with default arguments
	 * @param args Ignored 
	 */
	public static void main(String[] args) {
		String tempDirPath = System.getProperty("java.io.tmpdir");
		List<List<String>> imagePaths = DiffTest.getImagePaths();
		for(List<String> paths : imagePaths) {
			String standardPath = DiffTest.standardPath(paths);
			createStandard(standardPath, tempDirPath, paths);
		}

	}

	private static List<String> fileToLines(String filename) {
		List<String> lines = new LinkedList<String>();
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(new java.io.File(filename).getAbsolutePath()));
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return lines;
	}

	/**
	 * Returns the diff between the two given files
	 * @param pathOriginal The path to the original file
	 * @param pathRevised The path to the revised (new) file
	 * @return A representation of the diff
	 */
	public static String getDiff(String pathOriginal, String pathRevised) {
		List<String> originalLines, revisedLines;
		originalLines = fileToLines(pathOriginal);
		revisedLines = fileToLines(pathRevised);

		// Compute diff. Get the Patch object. Patch is the container for computed deltas.
		Patch patch = DiffUtils.diff(originalLines, revisedLines);
		StringBuilder diff = new StringBuilder();

		for (Delta delta : patch.getDeltas()) {
			diff.append(delta.toString());
			diff.append("\n");
		}

		return diff.toString();
	}
}
