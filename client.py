import xmlrpc.client
 
def main():
    # Create an XML-RPC client and connect to the server on localhost:8000 using the '/RPC2' path
    proxy = xmlrpc.client.ServerProxy("http://localhost:8000/RPC2")
 
    # Prompt user for input and ensure that the input is numeric (floats)
    try:
        m = float(input("Enter m: "))
        n = float(input("Enter n: "))
    except ValueError:
        print("Invalid input. Please enter numeric values.")
        return  # Exit the function if the input is invalid
    
    # Call the 'add' and 'subtract' methods remotely and get the results
    a = proxy.add(m, n)
    s = proxy.subtract(m, n)

    # Print the results of the operations
    print(f"Addition result: {a}")
    print(f"Subtraction result: {s}")

if __name__ == "__main__":
    # Run the main function to start the client when the script is executed
    main()


# import xmlrpc.client

# def main():
#     proxy = xmlrpc.client.ServerProxy("http://localhost:8000/RPC2")
#     add = proxy.add(5, 2)
#     sub = proxy.sub(5, 2)
#     print("Addition result:", add)
#     print("Subtraction result:", sub)

# if __name__ == "__main__":
#     main()















