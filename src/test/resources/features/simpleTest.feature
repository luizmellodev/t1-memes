Feature: Test Feature

    This is a test feature, intended for guidance purposes only

    Scenario Outline: The server is up and running
        Given the user triggers "<endpoint>"
        Then the server sould return status code "<status>"
        And the server says "<message>"

    Examples:
        | route | status | message       |
        | /     | 200    |  Hello World! |
