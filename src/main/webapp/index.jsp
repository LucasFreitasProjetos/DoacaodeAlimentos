<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doação de Alimentos - Home</title>
    <link rel="stylesheet" href="css/estilo.css">
</head>
<body>
    <nav class="navbar">
        <div class="container">
            <div class="logo">🍎 Doação de Alimentos</div>
            <ul class="nav-menu">
                <li><a href="index.jsp" class="active">Home</a></li>
                <li><a href="doador/listar.jsp">Doadores</a></li>
                <li><a href="instituicao/listar.jsp">Instituições</a></li>
                <li><a href="doacao/listar.jsp">Doações</a></li>
                <li><a href="app.html">Front-end</a></li>
            </ul>
        </div>
    </nav>

    <div class="hero">
        <div class="hero-content">
            <h1>Bem-vindo ao Sistema de Doação de Alimentos</h1>
            <p>Conectando doadores e instituições para um mundo melhor</p>
        </div>
    </div>

    <div class="container">
        <div class="dashboard">
            <div class="card">
                <div class="card-icon">👥</div>
                <h3>Doadores</h3>
                <p>Gerenciar doadores e suas informações</p>
                <a href="doador/listar.jsp" class="btn btn-primary">Acessar</a>
            </div>

            <div class="card">
                <div class="card-icon">🏛️</div>
                <h3>Instituições</h3>
                <p>Gerenciar instituições beneficentes</p>
                <a href="instituicao/listar.jsp" class="btn btn-primary">Acessar</a>
            </div>

            <div class="card">
                <div class="card-icon">📦</div>
                <h3>Doações</h3>
                <p>Registrar e acompanhar doações</p>
                <a href="doacao/listar.jsp" class="btn btn-primary">Acessar</a>
            </div>
        </div>

        <div class="info-section">
            <h2>Como Funciona</h2>
            <div class="steps">
                <div class="step">
                    <div class="step-number">1</div>
                    <h4>Cadastre Doadores</h4>
                    <p>Registre as pessoas que desejam realizar doações</p>
                </div>
                <div class="step">
                    <div class="step-number">2</div>
                    <h4>Cadastre Instituições</h4>
                    <p>Adicione as instituições que recebem as doações</p>
                </div>
                <div class="step">
                    <div class="step-number">3</div>
                    <h4>Registre Doações</h4>
                    <p>Conecte doadores e instituições através de doações</p>
                </div>
            </div>
        </div>
    </div>

    <footer class="footer">
        <p>&copy; 2026 Sistema de Doação de Alimentos. Todos os direitos reservados.</p>
    </footer>
</body>
</html>
