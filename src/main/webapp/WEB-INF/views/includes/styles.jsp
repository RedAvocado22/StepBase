<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

:root {
    --primary-color: #2563eb;
    --primary-dark: #1e40af;
    --secondary-color: #64748b;
    --success-color: #10b981;
    --danger-color: #ef4444;
    --warning-color: #f59e0b;
    --background: #f8fafc;
    --surface: #ffffff;
    --text-primary: #1e293b;
    --text-secondary: #64748b;
    --border-color: #e2e8f0;
    --shadow-sm: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
    --shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
    --shadow-lg: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
}

body {
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
    background-color: var(--background);
    color: var(--text-primary);
    line-height: 1.6;
    min-height: 100vh;
}

.container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
}

header {
    background-color: var(--surface);
    box-shadow: var(--shadow-sm);
    border-bottom: 1px solid var(--border-color);
    position: sticky;
    top: 0;
    z-index: 100;
}

nav {
    padding: 1rem 0;
}

nav .container {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.logo {
    font-size: 1.5rem;
    font-weight: 700;
    color: var(--primary-color);
    text-decoration: none;
}

.nav-links {
    display: flex;
    gap: 1.5rem;
    list-style: none;
    align-items: center;
}

.nav-links a {
    color: var(--text-secondary);
    text-decoration: none;
    font-weight: 500;
    transition: color 0.2s;
    padding: 0.5rem 0;
}

.nav-links a:hover {
    color: var(--primary-color);
}

.nav-links a.active {
    color: var(--primary-color);
}

main {
    padding: 2rem 0;
    min-height: calc(100vh - 200px);
}

.page-header {
    margin-bottom: 2rem;
}

.page-header h1,
.page-header h2 {
    color: var(--text-primary);
    font-size: 2rem;
    font-weight: 700;
    margin-bottom: 0.5rem;
}

.page-header p {
    color: var(--text-secondary);
}

.card {
    background: var(--surface);
    border-radius: 8px;
    box-shadow: var(--shadow-md);
    padding: 1.5rem;
    margin-bottom: 1.5rem;
    border: 1px solid var(--border-color);
}

.card-header {
    margin-bottom: 1rem;
    padding-bottom: 1rem;
    border-bottom: 1px solid var(--border-color);
}

.card-header h3 {
    color: var(--text-primary);
    font-size: 1.25rem;
    font-weight: 600;
}

.form-group {
    margin-bottom: 1.5rem;
}

.form-group label {
    display: block;
    margin-bottom: 0.5rem;
    color: var(--text-primary);
    font-weight: 500;
    font-size: 0.875rem;
}

.form-control {
    width: 100%;
    padding: 0.75rem;
    border: 1px solid var(--border-color);
    border-radius: 6px;
    font-size: 1rem;
    transition: border-color 0.2s, box-shadow 0.2s;
    background-color: var(--surface);
    color: var(--text-primary);
}

.form-control:focus {
    outline: none;
    border-color: var(--primary-color);
    box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
}

.form-control:invalid {
    border-color: var(--danger-color);
}

.btn {
    display: inline-block;
    padding: 0.75rem 1.5rem;
    font-size: 1rem;
    font-weight: 500;
    text-align: center;
    text-decoration: none;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: all 0.2s;
    line-height: 1.5;
}

.btn-primary {
    background-color: var(--primary-color);
    color: white;
}

.btn-primary:hover {
    background-color: var(--primary-dark);
    transform: translateY(-1px);
    box-shadow: var(--shadow-md);
}

.btn-secondary {
    background-color: var(--secondary-color);
    color: white;
}

.btn-secondary:hover {
    background-color: #475569;
}

.btn-success {
    background-color: var(--success-color);
    color: white;
}

.btn-success:hover {
    background-color: #059669;
}

.btn-danger {
    background-color: var(--danger-color);
    color: white;
}

.btn-danger:hover {
    background-color: #dc2626;
}

.btn-outline {
    background-color: transparent;
    border: 1px solid var(--border-color);
    color: var(--text-primary);
}

.btn-outline:hover {
    background-color: var(--background);
    border-color: var(--primary-color);
    color: var(--primary-color);
}

.btn-sm {
    padding: 0.5rem 1rem;
    font-size: 0.875rem;
}

.alert {
    padding: 1rem;
    border-radius: 6px;
    margin-bottom: 1.5rem;
    border: 1px solid;
}

.alert-error {
    background-color: #fef2f2;
    border-color: #fecaca;
    color: #991b1b;
}

.alert-success {
    background-color: #f0fdf4;
    border-color: #bbf7d0;
    color: #166534;
}

.alert-info {
    background-color: #eff6ff;
    border-color: #bfdbfe;
    color: #1e40af;
}

.table-container {
    overflow-x: auto;
    background: var(--surface);
    border-radius: 8px;
    box-shadow: var(--shadow-md);
    border: 1px solid var(--border-color);
}

table {
    width: 100%;
    border-collapse: collapse;
}

table thead {
    background-color: var(--background);
}

table th {
    padding: 1rem;
    text-align: left;
    font-weight: 600;
    color: var(--text-primary);
    border-bottom: 2px solid var(--border-color);
    font-size: 0.875rem;
    text-transform: uppercase;
    letter-spacing: 0.05em;
}

table td {
    padding: 1rem;
    border-bottom: 1px solid var(--border-color);
    color: var(--text-secondary);
}

table tbody tr:hover {
    background-color: var(--background);
}

table tbody tr:last-child td {
    border-bottom: none;
}

.product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 1.5rem;
    margin-top: 2rem;
}

.product-card {
    background: var(--surface);
    border-radius: 8px;
    box-shadow: var(--shadow-md);
    overflow: hidden;
    transition: transform 0.2s, box-shadow 0.2s;
    border: 1px solid var(--border-color);
    display: flex;
    flex-direction: column;
}

.product-card:hover {
    transform: translateY(-4px);
    box-shadow: var(--shadow-lg);
}

.product-card-body {
    padding: 1.5rem;
    flex-grow: 1;
    display: flex;
    flex-direction: column;
}

.product-card-title {
    font-size: 1.125rem;
    font-weight: 600;
    color: var(--text-primary);
    margin-bottom: 0.5rem;
}

.product-card-title a {
    color: var(--text-primary);
    text-decoration: none;
    transition: color 0.2s;
}

.product-card-title a:hover {
    color: var(--primary-color);
}

.product-card-description {
    color: var(--text-secondary);
    font-size: 0.875rem;
    margin-bottom: 1rem;
    flex-grow: 1;
    line-height: 1.5;
}

.product-card-price {
    font-size: 1.5rem;
    font-weight: 700;
    color: var(--primary-color);
    margin-bottom: 1rem;
}

.product-card-actions {
    display: flex;
    gap: 0.5rem;
    align-items: center;
}

.product-card-actions input[type="number"] {
    width: 80px;
    padding: 0.5rem;
    border: 1px solid var(--border-color);
    border-radius: 6px;
    text-align: center;
}

.stats-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 1.5rem;
    margin-bottom: 2rem;
}

.stat-card {
    background: var(--surface);
    padding: 1.5rem;
    border-radius: 8px;
    box-shadow: var(--shadow-md);
    border: 1px solid var(--border-color);
}

.stat-card-label {
    font-size: 0.875rem;
    color: var(--text-secondary);
    text-transform: uppercase;
    letter-spacing: 0.05em;
    margin-bottom: 0.5rem;
}

.stat-card-value {
    font-size: 2rem;
    font-weight: 700;
    color: var(--primary-color);
}

footer {
    background-color: var(--surface);
    border-top: 1px solid var(--border-color);
    padding: 2rem 0;
    margin-top: 4rem;
    text-align: center;
    color: var(--text-secondary);
    font-size: 0.875rem;
}

.text-center {
    text-align: center;
}

.text-right {
    text-align: right;
}

.mt-1 { margin-top: 0.5rem; }
.mt-2 { margin-top: 1rem; }
.mt-3 { margin-top: 1.5rem; }
.mt-4 { margin-top: 2rem; }

.mb-1 { margin-bottom: 0.5rem; }
.mb-2 { margin-bottom: 1rem; }
.mb-3 { margin-bottom: 1.5rem; }
.mb-4 { margin-bottom: 2rem; }

.d-flex {
    display: flex;
}

.justify-between {
    justify-content: space-between;
}

.align-center {
    align-items: center;
}

.gap-1 { gap: 0.5rem; }
.gap-2 { gap: 1rem; }
.gap-3 { gap: 1.5rem; }

@media (max-width: 768px) {
    .nav-links {
        flex-direction: column;
        gap: 0.5rem;
    }

    .product-grid {
        grid-template-columns: 1fr;
    }

    .stats-grid {
        grid-template-columns: 1fr;
    }

    nav .container {
        flex-direction: column;
        gap: 1rem;
    }
}
</style>

