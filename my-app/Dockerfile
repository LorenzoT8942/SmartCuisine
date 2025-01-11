# Use the Node.js image
FROM node:18

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json to the container
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application source code to the container
COPY . .

# Expose the React development server port
EXPOSE 3000

# Start the React development server
CMD ["npm", "start"]
