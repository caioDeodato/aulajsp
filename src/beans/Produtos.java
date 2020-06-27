package beans;

public class Produtos {
	
	private Long id;
	private String nomeProduto;
	private String quantidade;
	private String valor;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return "Produtos [id=" + id + ", nomeProduto=" + nomeProduto + ", quantidade=" + quantidade + ", valor=" + valor
				+ "]";
	}

}
