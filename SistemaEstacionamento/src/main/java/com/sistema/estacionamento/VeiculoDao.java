package com.sistema.estacionamento;

import javax.persistence.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class VeiculoDao<T> {
    private EntityManagerFactory emf;
    private EntityManager em;
    private static final Logger logger = Logger.getLogger(VeiculoDao.class.getName());

    public VeiculoDao() {
        emf = Persistence.createEntityManagerFactory("sistemaEstacionamento");
        em = emf.createEntityManager();
    }

    public void salvarVeiculo(T veiculo) {
        try {
            executeInsideTransaction(em -> em.persist(veiculo));
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException("Erro ao salvar a veiculo");
        }
    }

    public void atualizarVeiculo(T veiculo) {
        executeInsideTransaction(em -> em.merge(veiculo));
    }

    public void excluirVeiculo(String placa) {
        executeInsideTransaction(em -> {
            T veiculo = em.createQuery("SELECT v FROM " + getEntityClass().getSimpleName() + " v WHERE v.placa = :placa", getEntityClass())
                    .setParameter("placa", placa)
                    .getSingleResult();
            if (veiculo != null) {
                em.remove(veiculo);
            }
        });
    }

    public T consultarVeiculoPorPlaca(String placa) {
        try {
            return em.createQuery("SELECT v FROM " + getEntityClass().getSimpleName() + " v WHERE v.placa = :placa", getEntityClass())
                    .setParameter("placa", placa)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.SEVERE, "Erro ao consultar veículo por placa", e);
            return null;
            //throw new RuntimeException("Erro ao consultar veículo por placa", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao consultar veículo por placa", e);
            //throw new RuntimeException("Erro ao consultar veículo por placa", e);
            return null;
        }
    }

    public T consultarVeiculoImpressao(String placa) {
        try {
            return em.createQuery("SELECT v FROM " + getEntityClass().getSimpleName() + " v WHERE v.placa = :placa " +
                            "AND v.statusPagamento = :statusPagamento ORDER BY v.horaSaida DESC", getEntityClass())
                    .setParameter("placa", placa)
                    .setParameter("statusPagamento", "Pago")
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao consultar veículo por placa", e);
            throw new RuntimeException("Erro ao consultar veículo por placa", e);
        }
    }

    public List<T> consultarVeiculos() {
        try {
            TypedQuery<T> query = em.createQuery("SELECT v FROM " + getEntityClass().getSimpleName() + " v", getEntityClass());
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao consultar veículos", e);
            throw new RuntimeException("Erro ao consultar veículos", e);
        }
    }

    public void close() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    private void executeInsideTransaction(EntityManagerConsumer action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    @FunctionalInterface
    private interface EntityManagerConsumer {
        void accept(EntityManager em);
    }

    protected abstract Class<T> getEntityClass();
}
