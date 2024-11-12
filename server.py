# Import necessary modules from xmlrpc library
from xmlrpc.server import SimpleXMLRPCServer, SimpleXMLRPCRequestHandler

# Create a class with methods to be exposed for remote procedure calls
class Cal:
    # Method to add two numbers
    def add(self, x, y):
        return x + y

    # Method to subtract two numbers
    def subtract(self, x, y):
        return x - y

# Restrict the RPC server to only allow access to a specific path ('/RPC2') for security
class RequestHandler(SimpleXMLRPCRequestHandler):
    rpc_paths = ('/RPC2',)  # Tuple to define allowed RPC paths

def main():
    # Create and configure the server to listen on localhost:8000 with a custom request handler
    server = SimpleXMLRPCServer(('localhost', 8000), requestHandler=RequestHandler)

    # Register the class instance so its methods can be called remotely
    server.register_instance(Cal())

    # Print a message to indicate the server is listening for incoming RPC requests
    print("RPC Server is listening on port 8000...")

    # Start the server to listen indefinitely for incoming requests
    server.serve_forever()

if __name__ == "__main__":
    # Run the main function to start the server when the script is executed
    main()

# from xmlrpc.server import SimpleXMLRPCServer, SimpleXMLRPCRequestHandler

# class op:
#     def add(self, a, b):
#         print("add called with:", a, b)  # Debugging print
#         return a + b
#     def sub(self, a, b):
#         print("sub called with:", a, b)  # Debugging print
#         return a - b

# class r(SimpleXMLRPCRequestHandler):
#     rpc_path = ('/RPC2',)

# def main():
#     server = SimpleXMLRPCServer(('localhost', 8000), requestHandler=r)
#     server.register_instance(op())  # Register an instance of 'op'
#     print("Server running on port 8000...")
#     server.serve_forever()

# if __name__ == "__main__":
#     main()
