package Model;

import Dao.ConexaoDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoModel {
    private int codigo;
    private String nome;
    private String descricao;
    private double valor;
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    
    public static ProdutoModel obterProdutoPorCodigo (int codigo){
        String query = "SELECT * FROM PRODUTO WHERE codigo = ?";
        ResultSet resultSet = null;
        ProdutoModel prod = null;

        try (Connection conexao = ConexaoDao.conectar();
            PreparedStatement preparedStatement = conexao.prepareStatement(query)) {
            preparedStatement.setInt(1, codigo);
            resultSet = preparedStatement.executeQuery();

            // VERIF SE HÁ RESULTADOS
            if (resultSet != null && resultSet.next()) {
                //PREENCHE OBJETO CODIGO COM OS DADOS DO BANCO DE DADOS
                prod = new ProdutoModel();
                prod.setCodigo(resultSet.getInt("codigo"));
                prod.setNome(resultSet.getString("nome"));
                prod.setDescricao(resultSet.getString("descricao"));
                prod.setValor(resultSet.getDouble("valor"));
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
    return prod;
    }
}
