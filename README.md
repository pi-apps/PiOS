# Pi Open Source (PiOS)
To view a list of open source Pi applications navigate to the [PiOS App List](/list.md) 

To learn more about Pi Open Source and how to utilize it continue reading

## The License
The Pi Open Source Software License allows [Pi Community](https://minepi.com) Developers to create open source applications and tools for the Pi Ecosystem and foster collaboration among Pi community developers. In contrast to the MIT license the PiOS license grants unrestricted use of the software only within the Pi Ecosystem. Developers are encouraged to use this license when developing a Pi App.

## Creating a PiOS application 

We are offering projects github hosting under the `pi-apps` organization so that projects can be found easily. To request your own repo follow the steps below:

### 1. Submit a PR to the PiOS App List 
Create a PR on this repository adding your application's information to the [PiOS App List](/list.md) table. Follow the formatted row template given on that doc, and fill in the below information as incomplete submissions will be rejected.  
  
Information necessary for the PR to be approved:
- Category (If you need a new Category add it directly in the PR)
- Title 
  - Can be in the form of “App_Name for Pi”, “App_Name on Pi”, “App_Name”, but not in the form of “Pi App_Name” 
  to avoid confusion with apps developed by the Pi Core Team
- Description - Short description about what the application aims to achieve
- Programming Languages + Framework to be used
- Link to Pi Brainstorm project proposal (project name until feature is ready)

Information NOT necessary for the PR to be approved:
- Link To Repo - Core Team will fill this in upon PR approval
- Link to Testnet App - Project admin to send new PR later when available
- Link to Mainnet App - Project admin to send new PR later when available  

### 2. Pi Core Team will approve PR and create repository
We will approve your PR, create a repository under github.com/pi-apps and invite you as the admin of that repository. 

The github user that submits the Pull Request to the PiOS Application Library table will be given admin privileges to this newly created repository. This will grant you full admin access to the repository to add additional team members, and perform any sensitive or destructive actions like managing security, moving or even deleting the repository.  

Other Pi community developers can also contribute to the maintenance and improvement of the application and submit Pull Requests directly to the project. As the admin you will be responsible for responding to those PRs or you can delegate this responsibility to your team members. Even though the Pi Core Team is providing you access to the github.com/pi-apps namespace for convenience, it will not maintain your repository or application code. You are fully responsible for your code.

In the future should you wish to remove your application from the Pi Open Source Library you will be able to do this by simply moving your project out and sending a PR to remove the link to your code from the list. As an admin of your repository you can move it or remove it anytime without the approval of the Pi Core Team.    

### 3. Edit License in your application's code repository and start coding
To add the license to your code follow the below steps:
- Edit the Copyright line of the LICENSE file in your repo 

Note: source of the PiOS license is also available here [https://github.com/pi-apps/PiOS/blob/main/LICENSE](https://github.com/pi-apps/PiOS/blob/main/LICENSE)
