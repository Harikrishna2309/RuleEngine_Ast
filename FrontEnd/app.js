const apiUrl = 'http://localhost:8080/api/rules'; // Base API URL

// Function to create a rule
function createRule() {
    // Fetch input values
    const age = document.getElementById('ageInput').value;
    const operator = document.getElementById('operatorSelect').value;
    const department = document.getElementById('departmentInput').value;

    // Basic validation
    if (!age || !department) {
        document.getElementById('createRuleResult').innerText = 'Please fill out all fields';
        return;
    }

    // Build the rule string from input fields
    const ruleString = `age > ${age} ${operator} department = '${department}'`;

    // Prepare the payload for the API
    const data = { rule: ruleString };

    // Make the API call to create the rule
    fetch(`${apiUrl}/create`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('createRuleResult').innerText = `Created Rule ID: ${data.id}`;
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById('createRuleResult').innerText = 'Error creating rule';
    });
}

// Function to fetch and display all rules
// Function to fetch and display all rules
function fetchRules() {
    fetch(`${apiUrl}/allrules`)
    .then(response => response.json())
    .then(rules => {
        console.log('Fetched rules:', rules); // Log the fetched rules to see the structure

        const rulesList = document.getElementById('rulesList');
        rulesList.innerHTML = '';  // Clear the existing list

        // Check if the response is an array
        if (Array.isArray(rules)) {
            rules.forEach(rule => {
                const li = document.createElement('li');
                li.innerText = `ID: ${rule.id}, Rule: ${rule.ruleString}`;
                rulesList.appendChild(li);
            });
        } else {
            rulesList.innerHTML = 'No rules found or invalid data format';
        }
    })
    .catch(error => {
        console.error('Error fetching rules:', error);
    });
}

// combine rule
function combineRules() {
    const ruleIdsInput = document.getElementById('ruleIdsInput').value.trim();
    
    if (!ruleIdsInput) {
        document.getElementById('combineRuleResult').innerText = 'Please enter rule IDs to combine.';
        return;
    }

    const ruleIds = ruleIdsInput.split(',').map(id => id.trim());

    // Prepare the payload for the API
    const data = {
        rules: ruleIds
    };

    // Make the API call to combine rules
    fetch(`${apiUrl}/combine`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        document.getElementById('combineRuleResult').innerText = `Combined Rule ID: ${data.id}`;
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById('combineRuleResult').innerText = 'Error combining rules';
    });
}


// Function to evaluate a rule
function evaluateRule() {
    const ruleId = document.getElementById('evaluateRuleId').value;
    const evaluationData = document.getElementById('evaluationData').value;

    // Make the API call to evaluate the rule
    fetch(`${apiUrl}/evaluate?ruleId=${ruleId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: evaluationData
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('evaluateRuleResult').innerText = `Evaluation Result: ${data}`;
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById('evaluateRuleResult').innerText = 'Error evaluating rule';
    });
}

// Fetch the rules when the page loads
window.onload = function() {
    fetchRules();
};
