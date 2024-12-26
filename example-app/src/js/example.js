import { SecondaryDisplay } from 'capacitor-sec-display-plugin';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    SecondaryDisplay.echo({ value: inputValue })
}
