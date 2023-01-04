package com.example.evolutiongenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class WriterCSV {

    Statistics statistics;
    Configuration configuration;

    public WriterCSV(Statistics statistics, Configuration configuration) throws FileNotFoundException {
        this.statistics = statistics;
        this.configuration = configuration;
        prepareCSVFile();
    }

    private void prepareCSVFile() throws FileNotFoundException {
        if (configuration.isSaveToCSV()) {
            String[] variableToWrite = new String[7];
            variableToWrite[0] = "Day";
            variableToWrite[1] = "Population size";
            variableToWrite[2] = "Plants quantity";
            variableToWrite[3] = "Free field";
            variableToWrite[4] = "Most popular genotype";
            variableToWrite[5] = "Average energy level";
            variableToWrite[6] = "Average life length";
            writeDataToCSV(variableToWrite, configuration.getSimulationName());
        }
    }


    public void saveStatisticsToCSV() throws FileNotFoundException {
        if (configuration.isSaveToCSV()) {
            String[] variableToWrite = new String[7];
            variableToWrite[0] = Integer.toString(statistics.getSimulationDay());
            variableToWrite[1] = Integer.toString(statistics.getPopulationSize());
            variableToWrite[2] = Integer.toString(statistics.getPlantsQuantity());
            variableToWrite[3] = Integer.toString(statistics.getFreeFieldQuantity());
            variableToWrite[4] = statistics.getTheMostPopularGenotype().toString();
            variableToWrite[5] = Double.toString(statistics.getAverageEnergy());
            variableToWrite[6] = Double.toString(statistics.getAverageLifeLength());
            writeDataToCSV(variableToWrite, configuration.getSimulationName());
        }
    }

    private void writeDataToCSV(String[] data, String fileName) throws FileNotFoundException {
        File file = new File("E:\\Projekty AGH\\The first project\\evolution-generator\\src\\main\\java\\csvFile\\" + fileName + ".csv");
        try {
            FileWriter writer = new FileWriter(file, true);
            for (String text : data) {
                writer.append(text);
                writer.append(";");
            }
            writer.append("\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
