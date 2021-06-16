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
		// �̸�, ��ȣ, �̸��� �� ������, 400 ����ó��
		// �����͸� �������� ó���ϴ� ��Ŀ� �°� ������ �ʾ���
		if (contact.getName() == null || contact.getName().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// Ŭ���̾�Ʈ���� �Ѿ���� �����Ϳ� ���ؼ� ����
		contact.setCreatedTime(new Date().getTime());

		// repository.save(entity)
		// @Id: �ʵ� ���� ������ insert, ������ update
		return repo.save(contact);
	}

	// GET /contacts/1 -> contact��Ͽ��� id�� 1�� ���ڵ� ��ȸ
	@GetMapping(value = "/contacts/{id}")
	public Contact getContact(@PathVariable int id, HttpServletResponse res) {

		Optional<Contact> contact = repo.findById(id);

		// ���ҽ��� ������ 404 ������ �����
		if (contact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return contact.get();
	}

	// DELETE /contacts/1 -> contact��Ͽ��� id�� 1�� ���ڵ� ����
	@DeleteMapping(value = "/contacts/{id}")
	public boolean removeContact(@PathVariable int id, HttpServletResponse res) {

		Optional<Contact> contact = repo.findById(id);

		if (contact.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}
		// hard-delete: ������ DELETE ������ ���ڵ带 ����
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
		// id�� �����ϰ� ��ü �ʵ带 ������Ʈ��
		return repo.save(toUpdateContact);
	}
}