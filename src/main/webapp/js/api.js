// Configuração da URL base da API de forma dinâmica
function getApiUrl() {
    let path = window.location.pathname;
    // Remove o nome do arquivo da URL (ex: /listar.html)
    path = path.substring(0, path.lastIndexOf('/'));
    // Se estiver em um subdiretório (doador, instituicao, doacao), remove do path
    path = path.replace(/\/(doador|instituicao|doacao)$/, '');
    return `${window.location.origin}${path}/api`;
}

const apiUrl = getApiUrl();

// Função auxiliar para exibir mensagens de feedback na tela
function showMessage(text, type = 'info') {
    const messageContainer = document.getElementById('message');
    if (!messageContainer) return;
    messageContainer.textContent = text;
    messageContainer.className = `alert alert-${type}`;
    messageContainer.style.display = 'block';
    setTimeout(() => {
        messageContainer.style.display = 'none';
    }, 5000);
}

// Helper para obter parâmetros de consulta da URL (como ?id=...)
function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}
