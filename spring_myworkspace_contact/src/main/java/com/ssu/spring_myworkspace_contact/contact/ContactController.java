package com.ssu.spring_myworkspace_contact.contact;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

	private ContactRepository repo;

	@Autowired
	public ContactController(ContactRepository repo) {
		this.repo = repo;
	}

	@GetMapping(value = "/contacts")
	public List<Contact> getContactList() {
//		return repo.findAll();
		return repo.findAll(Sort.by("id").descending());
	}

	@PostMapping(value = "/contacts")
	public Contact addContact(@RequestBody Contact contact, HttpServletResponse res) {
		// 이름, 번호, 이메일 빈 값으면, 400 에러처리
		// 데이터를 서버에서 처리하는 양식에 맞게 보내지 않았음
		if (contact.getName() == null || contact.getName().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 클라이언트에서 넘어오는 데이터에 대해서 점검
		contact.setCreatedTime(new Date().getTime());

		// repository.save(entity)
		// @Id: 필드 값이 없으면 insert, 있으면 update
		return repo.save(contact);
	}

	// GET /contacts/1 -> contact목록에서 id가 1인 레코드 조회
	@GetMapping(value = "/contacts/{id}")
	public Contact getContact(@PathVariable int id, HttpServletResponse res) {

		Optional<Contact> contact = repo.findById(id);

		// 리소스가 없으면 404 에러를 띄어줌
		if (contact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return contact.get();
	}

	// DELETE /contacts/1 -> contact목록에서 id가 1인 레코드 삭제
	@DeleteMapping(value = "/contacts/{id}")
	public boolean removeContact(@PathVariable int id, HttpServletResponse res) {

		Optional<Contact> contact = repo.findById(id);

		if (contact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}
		// hard-delete: 실제로 DELETE 문으로 레코드를 삭제
		repo.deleteById(id);
		return true;
	}

	@PutMapping(value = "/contacts/{id}")
	public Contact modifyContact(@PathVariable int id, @RequestBody Contact contact, HttpServletResponse res) {

		Optional<Contact> findedContact = repo.findById(id);

		if (findedContact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		if (contact.getName() == null || contact.getName().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		Contact toUpdateContact = findedContact.get();
		toUpdateContact.setName(contact.getName());
		toUpdateContact.setPhone(contact.getPhone());
		toUpdateContact.setEmail(contact.getEmail());

		// repository.save(entity)
		// id값 제외하고 전체 필드를 업데이트함
		return repo.save(toUpdateContact);
	}
}