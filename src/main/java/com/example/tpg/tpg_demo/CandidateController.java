package com.example.tpg.tpg_demo;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.PersistenceContext;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CandidateController {

  private final CandidateRepository repository;

  @PersistenceContext
  private EntityManager em;

  CandidateController(CandidateRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/candidates")
  List<Candidate> all() {
    return repository.findAll();
  }

  @PostMapping("/candidates")
  Candidate newCandidate(@RequestBody Candidate newCandidate) {
    return repository.save(newCandidate);
  }

  @GetMapping("/candidates/{id}")
  Candidate findById(@PathVariable Long id) {

    return repository.findById(id)
        .orElseThrow(() -> new CandidateNotFoundException(id));
  }

  @GetMapping("/candidates/{fname}/{lname}")

  List<Candidate> findByName(@PathVariable String fname, @PathVariable String lname) {

    TypedQuery<Candidate> query = em.createQuery("select c from Candidate c where c.fname = ?1 and c.lname = ?2",
        Candidate.class);

    query.setParameter(1, fname);
    query.setParameter(2, lname);

    List<Candidate> result = query.getResultList();

    if (result.size() > 0) {
      return result;
    } else {
      throw new CandidateNotFoundException(fname, lname);
    }
  }

  @PutMapping("/candidates/{id}")
  Candidate updateCandidate(@RequestBody Candidate newCandidate, @PathVariable Long id) {

    return repository.findById(id)
        .map(Candidate -> {
          Candidate.setFname(newCandidate.getFname());
          Candidate.setLname(newCandidate.getLname());
          Candidate.setEmail(newCandidate.getEmail());
          Candidate.setScore(newCandidate.getScore());
          return repository.save(Candidate);
        })
        .orElseGet(() -> {
          newCandidate.setId(id);
          return repository.save(newCandidate);
        });
  }

  @DeleteMapping("/candidates/{id}")
  ResponseEntity<?> deleteCandidate(@PathVariable Long id) {

    repository.findById(id).orElseThrow(() -> new CandidateNotFoundException(id));
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
