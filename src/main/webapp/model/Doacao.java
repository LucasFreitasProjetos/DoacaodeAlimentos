import java.sql.Date;

public class Doacao{
    private int id;
    private int doadorId;
    private int instituicaoId;
    private String descricao;
    private String tipoAlimento;
    private int quantidade;
    private String status;
    private Date dataDoacao;

    public int getId() {
        return id;
    }
    public int getDoadorId() {
        return doadorId;
    }
    public int getInstituicaoId() {
        return instituicaoId;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getTipoAlimento() {
        return tipoAlimento;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public String getStatus() {
        return status;
    }
    public Date getDataDoacao() {
        return dataDoacao;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setDoadorId(int doadorId) {
        this.doadorId = doadorId;
    }
    public void setInstituicaoId(int instituicaoId) {
        this.instituicaoId = instituicaoId;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setTipoAlimento(String tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setDataDoacao(Date dataDoacao) {
        this.dataDoacao = dataDoacao;
    }
    public Doacao(String descricao, String tipoAlimento, int quantidade) {
        this.descricao = descricao;
        this.tipoAlimento = tipoAlimento;
        this.quantidade  = quantidade;
    }
    public Doacao() {
    }
}