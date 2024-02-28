# Use an official lightweight web server image
FROM nginx:alpine

# Copy the HTML, CSS, and JavaScript files to the appropriate directory
COPY . /usr/share/nginx/html

# Expose the port specified in your VS Live Server settings
EXPOSE 80