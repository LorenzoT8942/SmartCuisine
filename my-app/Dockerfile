FROM node:18 AS build

WORKDIR /app

COPY package*.json ./

# Installa le dipendenze
RUN npm install

# Copia il resto dei file del progetto
COPY . .

# builda l'app per la produzione
RUN npm run build

# Usa un'immagine Nginx come web server
FROM nginx:1.25-alpine

# Copia i file buildati nella directory di Nginx
COPY --from=build /app/build /usr/share/nginx/html

# Espone la porta 3006
EXPOSE 3006

# Avvia il server Nginx
CMD ["nginx", "-g", "daemon off;"]
