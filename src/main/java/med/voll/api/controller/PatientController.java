package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.patient.ListingPatientData;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientRepository;
import med.voll.api.domain.patient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("patients")
@SecurityRequirement(name = "bearer-key")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid PatientDataRegistry data, UriComponentsBuilder uriBuilder){
        var patient = new Patient(data);
        repository.save(patient);

        var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailedPatientData(patient));
    }

    @GetMapping
    public ResponseEntity<Page<ListingPatientData>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination){
       var page = repository.findAllByActiveTrue(pagination).map(ListingPatientData::new);
       return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid UpdatePatientDataRegistry data){
        var patient = repository.getReferenceById(data.id());
        patient.updateInfo(data);

        return ResponseEntity.ok(new DetailedPatientData(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var patient = repository.getReferenceById(id);
        patient.inactivate();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detailed(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);

        return ResponseEntity.ok(new DetailedPatientData(patient));
    }
}
