package com.example.tpg.tpg_demo;

import org.springframework.data.jpa.repository.JpaRepository;

interface CandidateRepository extends JpaRepository<Candidate, Long> {
   
}