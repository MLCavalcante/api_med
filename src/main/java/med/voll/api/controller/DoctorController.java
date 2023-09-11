package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.doctor.*;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.doctor.ListingDoctorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid MedicalDataRegistry data, UriComponentsBuilder uriBuilder) {
        var doctor = new Doctor(data);
        repository.save(doctor);

        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailedMedicalData(doctor));
    }

    @GetMapping
    public ResponseEntity<Page<ListingDoctorData>> list(@PageableDefault(size = 10, sort = "name") Pageable pagination) {
        var page = repository.findAllByActiveTrue(pagination).map(ListingDoctorData::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid UpdateMedicalDataRegistry data){
        var doctor = repository.getReferenceById(data.id());
        doctor.updateInfo(data);

        return ResponseEntity.ok(new DetailedMedicalData(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var doctor = repository.getReferenceById(id);
        doctor.inactivate();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detailed(@PathVariable Long id){
        var doctor = repository.getReferenceById(id);

        return ResponseEntity.ok(new DetailedMedicalData(doctor));
    }

}
