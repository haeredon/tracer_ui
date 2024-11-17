package com.tracer.ui.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.tracer.gen.api.TracesApi;
import com.tracer.gen.model.Trace;
import com.tracer.ui.services.TracesService;;

@RestController
public class TracesController implements TracesApi {

	TracesService tracesService;


	TracesController(TracesService tracesService) {
		this.tracesService = tracesService;
	}

	public ResponseEntity<List<Trace>> getTraces() {
		return new ResponseEntity<List<Trace>>(tracesService.getTraces(10, 0), HttpStatus.OK);
        
    }

}