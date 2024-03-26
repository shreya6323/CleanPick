import React from 'react';
import './Discover.css'; // You can define your CSS styles in App.css or inline as per your preference
import one from '../assets/71.png';
const chemicals = [
    { name: "Acetone", score: 3 },
    { name: "Benzene", score: 8 },
    { name: "Chloroform", score: 6 },
    { name: "Formaldehyde", score: 5 },
    { name: "Hydrochloric Acid", score: 9 },
    { name: "Hydrochloric Acid", score: 10 },
    { name: "Hydrochloric Acid", score: 10 }
];

function ChemicalCard({ name, score }) {
    return (
        <div className="card">
            <div className="card-title">{name}</div>
            <div className="card-score"><img src={one} alt='score' height="50px" width="50px"  /></div>
        </div>
    );
}

function ChemicalList() {
    return (
        <div className="chemical-list">
            {chemicals.map((chemical, index) => (
                <ChemicalCard key={index} name={chemical.name} score={chemical.score} />
            ))}
        </div>
    );
}

function App() {
    return (
        <div className="App">
            <h2>Chemical Toxicity List</h2>
            <ChemicalList />
        </div>
    );
}

export default App;
