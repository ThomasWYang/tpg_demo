package com.example.tpg.tpg_demo;

public class CandidateNotFoundException extends RuntimeException {
    CandidateNotFoundException(Long id) {
        super("Could not find candidate " + id);
    }

    CandidateNotFoundException(String fname, String lname) {
        super("Could not find candidate " + fname + " " + lname);
    }

    CandidateNotFoundException(Integer floor) {
        super("Could not find candidate with score greater than " + floor);
    }
}
