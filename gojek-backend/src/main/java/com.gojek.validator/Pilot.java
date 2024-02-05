package com.gojek.validator;

import com.gojek.client.UrlWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class Pilot<X> {
    Logger logger = LoggerFactory.getLogger(Pilot.class);

    private final ResponseExecutor rExecutor;
    private final List<UrlWrap> endpointsList;

    public Pilot(ResponseExecutor dataProcessor, List<UrlWrap> endpointsList) {
        this.rExecutor = dataProcessor;
        this.endpointsList = endpointsList;
    }

    /**
     * Function to get Request Data File from Given File or File Path
     */


    public File getRequestDataFile(X file) {

        if (file instanceof String) {
            try {
                return new File((String) file);
            } catch (Exception e) {
                logger.info("Unable to Process File");
                return null;
            }
        } else if (file instanceof File) {
            try {
                return (File) file;
            } catch (Exception e) {
                logger.info("Unable to Process File");
                return null;
            }
        }
        return null;
    }


    /**
     * Compare URL wrapper objects using streams in Parallel
     */
    public void runParallelComparatorWithStreams(X leftFile, X rightFile) {
        File inputFileLeft = getRequestDataFile(leftFile);
        File inputFileRight = getRequestDataFile(rightFile);
        if (inputFileLeft == null || inputFileRight == null) {
            logger.info("Unable to Process Data Files");
            return;
        }
        generate(inputFileLeft, inputFileRight);
        endpointsList.stream().parallel().forEach(rExecutor::execute);
    }


    /**
     * Reads the data files and prepare list of URL Wrappers
     */

    private void generate(File leftFile, File rightFile) {
        try {
            BufferedReader firstFileReader = new BufferedReader(new FileReader(leftFile));
            BufferedReader secondFileReader = new BufferedReader(new FileReader(rightFile));
            String urlFromFile1, urlFromFile2;
            while ((urlFromFile1 = firstFileReader.readLine()) != null) {
                if ((urlFromFile2 = secondFileReader.readLine()) != null) {
                    endpointsList.add(new UrlWrap(urlFromFile1, urlFromFile2));
                }
            }
        } catch (FileNotFoundException fnfe) {
            logger.info("Unable to find specified file");
        } catch (IOException ioe) {
            logger.info("Unable to read endpoints from given file");
            logger.info(ioe.getMessage());
        }
    }


}
