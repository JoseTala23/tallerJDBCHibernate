package org.springframework.samples.petclinic.owner;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.visit.Visit;

@Entity
@Table(name = "factura")
public class Bill {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Digits(integer=10,fraction=0)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "bill", cascade = CascadeType.ALL)
	private Visit visit;
	
	@Column(name = "fecha")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date fecha;
	
	@Column(name = "cuantia")
	private Double cuantia;

	
	public Bill() {
		
	}
	
	public Long getId() {
		return id;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getCuantia() {
		return cuantia;
	}

	public void setCuantia(Double cuantia) {
		this.cuantia = cuantia;
	}
	
}
