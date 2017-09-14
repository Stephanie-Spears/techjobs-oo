package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job theJob = jobData.findById(id);
        model.addAttribute("job", theJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if(errors.hasErrors()) {
            return "new-job";
        }

        // take the values from the form
        String jobName = jobForm.getName();
        int employerId = jobForm.getEmployerId();
        int locationId = jobForm.getLocationId();
        int positionTypeId = jobForm.getPositionTypeId();
        int coreCompetencyId = jobForm.getCoreCompetencyId();

        //use the id from the form to find corresponding objects
        Employer employer = jobData.getEmployers().findById(employerId);
        Location location = jobData.getLocations().findById(locationId);
        PositionType positionType = jobData.getPositionTypes().findById(positionTypeId);
        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(coreCompetencyId);

        //instantiate the new job object
        Job newJob = new Job(jobName,employer,location,positionType,coreCompetency);

        //add the job object to jobdata
        jobData.add(newJob);

        return "redirect:/job?id=" + newJob.getId();
    }
}
