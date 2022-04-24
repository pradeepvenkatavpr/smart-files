package org.smartfiles.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ck_files")
public class Files {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ck_file_id")
	private Long id;
	
	
}
