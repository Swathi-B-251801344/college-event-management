
package college_event_management;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;
    private final OrganizerRepository organizerRepository;
    private final PaymentRepository paymentRepository;
    private final EventOrganizerRepository eventOrganizerRepository;

    


public HomeController(
        EventRepository eventRepository,
        RegistrationRepository registrationRepository,
        StudentRepository studentRepository,
        OrganizerRepository organizerRepository,
        PaymentRepository paymentRepository,
        EventOrganizerRepository eventOrganizerRepository) {

    this.eventRepository = eventRepository;
    this.registrationRepository = registrationRepository;
    this.studentRepository = studentRepository;
    this.organizerRepository = organizerRepository;
    this.paymentRepository = paymentRepository;
    this.eventOrganizerRepository = eventOrganizerRepository;
}




    @GetMapping("/")
    public String home(Model model) {
        List<Event> events = eventRepository.findAll();
        model.addAttribute("events", events);
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registration", new Registration());
        model.addAttribute("events", eventRepository.findAll());
        return "register";
    }

    @PostMapping("/register")
    public String registerStudent(
            @ModelAttribute Registration registration,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (registration.getStudentName() == null
                || registration.getStudentName().isBlank()
                || registration.getRollNumber() == null
                || registration.getRollNumber().isBlank()
                || registration.getDepartment() == null
                || registration.getDepartment().isBlank()
                || registration.getEventId() == null
                || registration.getEventId() <= 0) {

            model.addAttribute("error", "Please fill in all fields.");
            model.addAttribute("registration", registration);
            model.addAttribute("events", eventRepository.findAll());
            return "register";
        }

        String message = registrationRepository.register(registration);

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/register";
    }
    
@GetMapping("/registrations")
public String viewRegistrations(Model model) {
    model.addAttribute("registrations", registrationRepository.findAll());
    return "registrations";
}

@GetMapping("/search-registration")
public String searchRegistration(
        @org.springframework.web.bind.annotation.RequestParam(
                name = "rollNumber", required = false) String rollNumber,
        Model model) {

    model.addAttribute("rollNumber", rollNumber);

    if (rollNumber != null && !rollNumber.isBlank()) {
        model.addAttribute(
                "registrations",
                registrationRepository.searchByRollNumber(rollNumber.trim()));
    } else {
        model.addAttribute("registrations", java.util.Collections.emptyList());
    }

    return "search-registration";
}

@GetMapping("/cancel-registration")
public String showCancelRegistration(Model model) {
    model.addAttribute("registrations", registrationRepository.findAll());
    return "cancel-registration";
}

@PostMapping("/cancel-registration")
public String cancelRegistration(
        @org.springframework.web.bind.annotation.RequestParam String rollNumber,
        @org.springframework.web.bind.annotation.RequestParam int eventId,
        Model model) {

    String message = registrationRepository.cancelRegistration(rollNumber, eventId);

    model.addAttribute("message", message);
    model.addAttribute("registrations", registrationRepository.findAll());

    return "cancel-registration";
}
@GetMapping("/students")
public String viewStudents(Model model) {
    model.addAttribute("students", studentRepository.findAll());
    return "students";
}

@GetMapping("/organizers")
public String viewOrganizers(Model model) {
    model.addAttribute("organizers", organizerRepository.findAll());
    return "organizers";
}

@GetMapping("/payments")
public String viewPayments(Model model) {
    model.addAttribute("payments", paymentRepository.findAll());
    return "payments";
}

@GetMapping("/event-organizers")
public String viewEventOrganizers(Model model) {
    model.addAttribute(
        "assignments", eventOrganizerRepository.findAll());
    return "event-organizers";
}
@GetMapping("/edit-event")
public String showEditEvent(
        @RequestParam(name = "eventId", required = false) Integer eventId,
        Model model) {

    if (eventId == null) {
        model.addAttribute("events", eventRepository.findAll());
        return "edit-event";
    }

    Event event = eventRepository.findById(eventId);

    if (event == null) {
        model.addAttribute("error", "Event not found.");
        model.addAttribute("events", eventRepository.findAll());
        return "edit-event";
    }

    model.addAttribute("event", event);
    return "edit-event";
}

@PostMapping("/update-event")
public String updateEvent(
        @RequestParam int eventId,
        @RequestParam String eventName,
        @RequestParam String eventDate,
        @RequestParam String venue,
        @RequestParam String description,
        @RequestParam int availableSeats,
        RedirectAttributes redirectAttributes) {

    if (eventName.isBlank() || eventDate.isBlank()
            || venue.isBlank() || description.isBlank()
            || availableSeats < 0) {

        redirectAttributes.addFlashAttribute(
                "message", "Please enter valid event details.");
        return "redirect:/edit-event?eventId=" + eventId;
    }

    Event event = new Event(
            eventId, eventName, eventDate, venue,
            description, availableSeats);

    boolean updated = eventRepository.update(event);

    redirectAttributes.addFlashAttribute(
            "message",
            updated ? "Event details updated successfully."
                    : "Event not found.");

    return "redirect:/";
}
}
