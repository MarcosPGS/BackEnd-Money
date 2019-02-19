package com.marcos.money.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.money.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>,CategoriaRepositoryQuery {



}
