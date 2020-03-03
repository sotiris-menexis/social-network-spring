package com.sotosmen.socialnetwork.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sotosmen.socialnetwork.exception.ResourceException;
import com.sotosmen.socialnetwork.repository.UserRepository;
import com.sotosmen.socialnetwork.strings.Strings;
/*
 * Δημιουργία κλάσης και δήλωση της κλάσης ως RestController η οποία περιέχει τα
 * annotations Controller και ResponseBody. Αυτό δηλώνει ότι οποιοδήποτε RequestMapping
 * της κλάσης θα επιστρέψει HttpResponse.
 */
@RestController
public class UserController {
	/*
	 * Το autowired annotation λέει στο dependency injection container της spring να
	 * δημιουργήσει μία εξάργηση μεταξύ αυτής της κλάσης και του UserRepository.
	 */
	@Autowired
	UserRepository userRepository;
	/*
	 * Δημιουργία GetMapping στο uri users. Αυτό σημαίνει ότι θα εκτελεστεί η παρακάτω
	 * μέθοδος μόλις γίνει http get request στο localhost:8080/users και θα επιστραφεί
	 * μία λίστα αντικειμένων User σε μορφή json. Λόγω του ResponseBody που
	 * εμπεριέχεται στο RestController μορφή της λίστας θα είναι json. Αν η βάση είναι
	 * άδεια τότε θα επιστραφεί αντίστοιχο μήνυμα και HttpStatus NOT FOUND.
	 */
	@GetMapping("/users")
	public List<User> getUsers() {
		if (userRepository.count() == 0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noUsers);
		}
		return userRepository.findAll();
	}
	/*
	 * Δημιουργία PostMapping στο uri users. Αυτό σημαίνει ότι θα εκτελεστεί η παρακάτω
	 * μέθοδος μόλις γίνει http post request στο localhost:8080/users και επειδή είναι
	 * void δεν θα επιστραφεί τίποτα. Η μέθοδος δημιουργεί έναν χρήστη και τον
	 * αποθηκεύει στη βάση δεδομένων μέσω του αντικειμένου Repository. Αν υπάρχει ήδη
	 * ο χρήστης επιστρέφεται ένα μύνημα με HttpStatus FORBIDDEN.
	 */
	@PostMapping("/users")
	public void createUser(@RequestBody User user) {
		if (userExists(user.getUsername())) {
			throw new ResourceException(HttpStatus.FORBIDDEN, Strings.userEx);
		}
		userRepository.save(user);
	}
	/*
	 * Δημιουργία PutMapping στο uri users. Αυτό σημαίνει ότι θα εκτελεστεί η παρακάτω
	 * μέθοδος μόλις γίνει http post request στο localhost:8080/users και επειδή είναι
	 * void δεν θα επιστραφεί τίποτα. H μέθοδος ενημερώνει τον χρήστη αν υπάρχει και 
	 * τον αποθηκεύει στη βάση δεδομένων μέσω του αντικειμένου Repository. Αν δεν
	 * υπάρχει ο χρήστης τότε επιστρέφει ένα HttpStatus NOT FOUND με αντίστοιχο
	 * μήνυμα.
	 */
	@PutMapping("/users")
	public void updateUser(@RequestBody User user) {
		if (userExists(user.getUsername())) {
			userRepository.deleteById(user.getUsername());
			userRepository.save(user);
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noUser);
		}
	}
	/*
	 * Δημιουργία DeleteMapping στο uri users. Αυτό σημαίνει ότι θα εκτελεστεί η παρακάτω
	 * μέθοδος μόλις γίνει http post request στο localhost:8080/users και επειδή είναι
	 * void δεν θα επιστραφεί τίποτα. Η μέθοδος διαγράφει τους χρήστες που υπάρχουν
	 * στη βάση δεδομένων (αν υπάρχουν), αλλιώς στέλνει πίσω ένα μήνυμα με HttpStatus
	 * NOT FOUND.
	 */
	@DeleteMapping("/users")
	public void deleteAllUsers() {
		if (userRepository.count() == 0) {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noUsers);
		} else {
			userRepository.deleteAll();
		}
	}
	/*
	 * Δημιουργία GetMapping στο uri users. Αυτό σημαίνει ότι θα εκτελεστεί η παρακάτω
	 * μέθοδος μόλις γίνει http post request στο localhost:8080/users/{username} και
	 * επειδή είναι void δεν θα επιστραφεί τίποτα. Η μέθοδος επιστρέφει τον χρήστη με
	 * το αντιστοιχο username που έχουμε περάσει στο uri (το annotation PathVariable
	 * δηλώνει ότι η μεταβλητή που ακολουθεί είναι η μεταβλητή που έχουμε περάσει στο
	 * uri. Αν ο χρήστης δεν υπάρχει τότε επιστρέφουμε αντίστοιχο μήνυμα με 
	 * HttpStatus NOT FOUND.
	 */
	@GetMapping("/users/{username}")
	public User getUser(@PathVariable String username) {
		if (userExists(username)) {
			return userRepository.findById(username).get();
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noUser);
		}
	}
	/*
	 * Δημιουργία DeleteMapping στο uri users. Αυτό σημαίνει ότι θα εκτελεστεί η παρακάτω
	 * μέθοδος μόλις γίνει http post request στο localhost:8080/users/{username} και 
	 * επειδή είναι void δεν θα επιστραφεί τίποτα. Η μέθοδος διαγράφει έναν 
	 * συγκεκριμένο χρήστη με το username το οποίο περνάμε μέσω του uri. Αν ο χρήστης δεν
	 * υπάρχει τότε επιστρέφουμε αντίστοιχο μήνυμα και HttpStatus NOT FOUND.
	 */
	@DeleteMapping("/users/{username}")
	public void deleteUser(@PathVariable String username) {
		if (userExists(username)) {
			userRepository.deleteById(username);
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, Strings.noUser);
		}
	}

	public boolean userExists(String username) {
		if (userRepository.findById(username).isPresent()) {
			return true;
		} else {
			return false;
		}
	}
}
