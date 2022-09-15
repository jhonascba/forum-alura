package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    //Busca por filtros, aonde o titulo é um atributo da entidade
//    List<Topico> findByTitulo(String nomeCurso);

    //Busca por relacionamento - Curso se refere a entidade, e nome ao atributo da entidade
    //também é possível utilizar o padrão Entidade_Atributo
    Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);

    //Se não não respeitar o padrão acima, deverá implementar a query por conta própria
    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
    List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
}
