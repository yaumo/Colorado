FROM node:latest

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Install app dependencies
COPY package.json /usr/src/app/
RUN npm install

# Bundle app source
COPY . /usr/src/app
RUN npm install -g webpack
RUN npm install -g babel-cli
RUN npm install react-fetch 
RUN npm install node-http-proxy
RUN webpack

EXPOSE 8081
CMD [ "babel-node", "server.js" ]
