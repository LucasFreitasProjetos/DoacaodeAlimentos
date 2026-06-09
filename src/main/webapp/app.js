const basePath = window.location.pathname.replace(/\/[^/]*$/, '');
const apiUrl = `${window.location.origin}${basePath}/api`;

function showMessage(text, type = 'info') {
    const message = document.getElementById('message');
    message.textContent = text;
    message.className = `alert alert-${type}`;
    message.style.display = 'block';
    setTimeout(() => {
        message.style.display = 'none';
    }, 5000);
}

function renderTable(columns, rows) {
    if (!rows || rows.length === 0) {
        return '<div class="empty-state">Nenhum registro encontrado.</div>';
    }
    const header = columns.map(col => `<th>${col}</th>`).join('');
    const body = rows.map(row => {
        const cells = columns.map(col => `<td>${row[col] !== undefined ? row[col] : ''}</td>`).join('');
        return `<tr>${cells}</tr>`;
    }).join('');
    return `<table><thead><tr>${header}</tr></thead><tbody>${body}</tbody></table>`;
}

async function loadDoadores() {
    try {
        const response = await fetch(`${apiUrl}/doador`);
        const data = await response.json();
        const columns = ['id', 'nome', 'email'];
        document.getElementById('doadoresList').innerHTML = renderTable(columns, data);
        showMessage('Doadores carregados com sucesso.', 'success');
        populateSelect('doacaoDoadorId', data, 'nome');
        return data;
    } catch (error) {
        showMessage('Erro ao carregar doadores.', 'danger');
        console.error(error);
        return [];
    }
}

async function loadInstituicoes() {
    try {
        const response = await fetch(`${apiUrl}/instituicao`);
        const data = await response.json();
        const columns = ['id', 'nome', 'endereco'];
        document.getElementById('instituicoesList').innerHTML = renderTable(columns, data);
        showMessage('Instituições carregadas com sucesso.', 'success');
        populateSelect('doacaoInstituicaoId', data, 'nome');
        return data;
    } catch (error) {
        showMessage('Erro ao carregar instituições.', 'danger');
        console.error(error);
        return [];
    }
}

async function loadDoacoes() {
    try {
        const response = await fetch(`${apiUrl}/doacao`);
        const data = await response.json();
        const columns = ['id', 'doadorId', 'instituicaoId', 'descricao', 'dataDoacao'];
        document.getElementById('doacoesList').innerHTML = renderTable(columns, data);
        showMessage('Doações carregadas com sucesso.', 'success');
    } catch (error) {
        showMessage('Erro ao carregar doações.', 'danger');
        console.error(error);
    }
}

function populateSelect(selectId, items, labelField) {
    const select = document.getElementById(selectId);
    if (!select) {
        return;
    }
    select.innerHTML = '<option value="">Selecione...</option>';
    if (!items || !items.length) {
        select.innerHTML = '<option value="">Nenhum registro disponível</option>';
        return;
    }
    items.forEach(item => {
        const option = document.createElement('option');
        option.value = item.id;
        option.textContent = item[labelField] ? `${item.id} - ${item[labelField]}` : item.id;
        select.appendChild(option);
    });
}

async function createDoador() {
    const nome = document.getElementById('doadorNome').value.trim();
    const email = document.getElementById('doadorEmail').value.trim();
    if (!nome || !email) {
        showMessage('Nome e email são obrigatórios.', 'warning');
        return;
    }
    try {
        const response = await fetch(`${apiUrl}/doador`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nome, email })
        });
        if (!response.ok) {
            throw new Error('Falha ao cadastrar doador');
        }
        document.getElementById('doadorNome').value = '';
        document.getElementById('doadorEmail').value = '';
        loadDoadores();
        showMessage('Doador cadastrado com sucesso.', 'success');
    } catch (error) {
        showMessage('Erro ao cadastrar doador.', 'danger');
        console.error(error);
    }
}

async function createInstituicao() {
    const nome = document.getElementById('instituicaoNome').value.trim();
    const endereco = document.getElementById('instituicaoEndereco').value.trim();
    if (!nome || !endereco) {
        showMessage('Nome e endereço são obrigatórios.', 'warning');
        return;
    }
    try {
        const response = await fetch(`${apiUrl}/instituicao`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nome, endereco })
        });
        if (!response.ok) {
            throw new Error('Falha ao cadastrar instituição');
        }
        document.getElementById('instituicaoNome').value = '';
        document.getElementById('instituicaoEndereco').value = '';
        loadInstituicoes();
        showMessage('Instituição cadastrada com sucesso.', 'success');
    } catch (error) {
        showMessage('Erro ao cadastrar instituição.', 'danger');
        console.error(error);
    }
}

async function createDoacao() {
    const doadorId = document.getElementById('doacaoDoadorId').value;
    const instituicaoId = document.getElementById('doacaoInstituicaoId').value;
    const descricao = document.getElementById('doacaoDescricao').value.trim();
    const dataDoacao = document.getElementById('doacaoData').value;

    if (!doadorId || !instituicaoId || !descricao || !dataDoacao) {
        showMessage('Todos os campos da doação são obrigatórios.', 'warning');
        return;
    }
    try {
        const response = await fetch(`${apiUrl}/doacao`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                doadorId: Number(doadorId),
                instituicaoId: Number(instituicaoId),
                descricao,
                dataDoacao
            })
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Falha ao cadastrar doação');
        }
        document.getElementById('doacaoDoadorId').value = '';
        document.getElementById('doacaoInstituicaoId').value = '';
        document.getElementById('doacaoDescricao').value = '';
        document.getElementById('doacaoData').value = '';
        await loadDoacoes();
        showMessage('Doação cadastrada com sucesso.', 'success');
    } catch (error) {
        showMessage(`Erro ao cadastrar doação: ${error.message}`, 'danger');
        console.error(error);
    }
}

window.addEventListener('load', async () => {
    await loadDoadores();
    await loadInstituicoes();
    await loadDoacoes();
});
