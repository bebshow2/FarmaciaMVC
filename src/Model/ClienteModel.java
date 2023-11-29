package Model;

import Dao.ConexaoDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteModel {
    private int id; 
    private String nome;
    private String cpf;
    private String endereco;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }     
    
    public static ClienteModel obterClientePorId (int id){
        String query = "SELECT * FROM CLIENTE WHERE id = ?";
        ResultSet resultSet = null;
        ClienteModel cli = null;

        try (Connection conexao = ConexaoDao.conectar();
            PreparedStatement preparedStatement = conexao.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

         // VERIF SE HÁ RESULTADOS
            if (resultSet != null && resultSet.next()) {
             //PREENCHE OBJETO CLIENTE COM OS DADOS DO BANCO DE DADOS
                cli = new ClienteModel();
                cli.setId(resultSet.getInt("id"));
                cli.setCpf(resultSet.getString("cpf"));
                cli.setNome(resultSet.getString("nome"));
                cli.setEndereco(resultSet.getString("endereco"));
            
            }
        } 
        catch (SQLException e) {
            e.printStackTrace(); 
        } finally {
         // FECHA O RESULTSET SE PRECISAR
        try {
            if (resultSet != null) resultSet.close();
        } 
        catch (SQLException e) {
            e.printStackTrace(); // EXCESSAO SQL SE FECHAR
        }
     }
     return cli;
    }
}
