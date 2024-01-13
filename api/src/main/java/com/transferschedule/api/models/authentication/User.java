package com.transferschedule.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor()
@Table(name="usuario")
@Entity(name="usuario")
public class Usuario extends BaseEntity {
	@Column(name = "des_nome")
	private String desNome;
	@Column(name = "des_email")
	private String desEmail;
	@Column(name = "cod_role")
	private long codRole;
	@Column(name = "hash_password")
	private String hashPassword;

	@Override
	public void isValid(){
		if (desNome == null || desNome.isEmpty()) {
			throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
		}

		if (desEmail != null && !desEmail.isEmpty()) {
			String regex = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(desEmail);
			if(!matcher.matches())
				throw new IllegalArgumentException("Formato de e-mail não compativel");
		}else{
			throw new IllegalArgumentException("Formato de e-mail não compativel");
		}

		if (codRole < 0) {
			throw new IllegalArgumentException("Id Role não pode ser negativo");
		}

		if (hashPassword != null && !hashPassword.isEmpty()) {
			String regex = "^[a-fA-F0-9]{32}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(hashPassword);

			if(!matcher.matches())
				throw new IllegalArgumentException("Hash de senha não compativel");
		} else {
			throw new IllegalArgumentException("Hash de senha não compativel");
		}
	}
}
