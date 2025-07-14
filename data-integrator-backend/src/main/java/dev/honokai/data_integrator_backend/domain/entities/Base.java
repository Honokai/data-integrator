package dev.honokai.data_integrator_backend.domain.entities;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

@MappedSuperclass
public class Base {
	@Id
	private String id;

	@PrePersist
	public void generateUlid() {
		if (this.id == null) {
			this.id = UlidCreator.getUlid().toString();
		}
	}

	public Base() {
		// TODO Auto-generated constructor stub
	}

	public Base(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		Ulid.from(id);

		this.id = id;
	}
}
