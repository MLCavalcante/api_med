package med.voll.api.controller;

import med.voll.api.domain.appointment.AppointmentDetailsData;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.domain.appointment.AppointmentScheduling;
import med.voll.api.domain.doctor.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AppointmentScheduleData> appointmentScheduleDataJSON;

    @Autowired
    private JacksonTester<AppointmentDetailsData> appointmentDetailsDataJSON;

    @MockBean
    private AppointmentScheduling appointmentScheduling;


    @Test
    @DisplayName("Should return http status code 400 when information is invalid.")
    @WithMockUser
    void schedule_case1() throws Exception{
        var response = mvc.perform(post("/appointments")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }


    @Test
    @DisplayName("Should return http status code 200 when information is valid.")
    @WithMockUser
    void schedule_case2() throws Exception{
        var date = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.GYNECOLOGY;

        var detailData = new AppointmentDetailsData(null, 1l, 2l, date, specialty);

        when(appointmentScheduling.schedule(any())).thenReturn(detailData);

        var response = mvc.perform(post("/appointments").contentType(MediaType.APPLICATION_JSON)
                .content(appointmentScheduleDataJSON.write(
                 new AppointmentScheduleData(1l, 2l, date, specialty)
                ).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var returnJson = appointmentDetailsDataJSON.write(
            detailData
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(returnJson);

    }

}




