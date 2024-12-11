FROM node:20-alpine AS build

# Definir diretório de trabalho dentro do container
WORKDIR /app

# Copiar os arquivos package.json e package-lock.json para instalar dependências
COPY package*.json ./

# Instalar dependências e Angular CLI
RUN npm install -g @angular/cli && npm install

# Copiar o restante dos arquivos do projeto
COPY . .

# Expor a porta 4200 para o servidor do Angular
EXPOSE 4200

# Comando para iniciar o servidor Angular
CMD ["ng", "serve", "--host", "0.0.0.0", "--port", "4200", "--disable-host-check"]
