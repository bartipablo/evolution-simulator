package com.example.evolutiongenerator;

import java.util.Random;

public class Gene {

    private final int genomeLength;
    private int[] genome;

    //constructors------------------------
    public Gene(int genomeLength) {
        this.genomeLength = genomeLength;
        generateRandomGenome();
    }

    public Gene(int[] genome) {
        this.genomeLength = genome.length;
        this.genome = genome;
    }
    //------------------------------------

    private void generateRandomGenome() {
        Random random = new Random();
        genome = new int[genomeLength];
        for (int i = 0; i < genomeLength; i++) {
            int randomGenome = random.nextInt(0, 8);
            genome[i] = randomGenome;
        }
    }

    public int[] getGenome() {
        return genome;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

}
