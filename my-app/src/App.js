import logo from './logo.svg';
import './App.css';
import './CSS/home-shopping-list.css'
import {useEffect} from "react";

const ingredients = [
    { name: 'Eggs', quantity: '428g' },
    { name: 'Salt', quantity: '382g' },
    { name: 'Sugar', quantity: '75g' },
    { name: 'Yeast', quantity: '194g' },
    { name: 'Cocoa Powder', quantity: '373g' }
];


const generateIngredientCards = (ingredients) => {
    return ingredients.map((ingredient, index) => (
        <IngredientCard
            key={index}
            name={ingredient.name}
            quantity={ingredient.quantity}
        />
    ));
};

function App() {
    useEffect(() => {
        // Set background color when the component mounts
        document.body.style.backgroundColor = '#32cd32';
        // If you want an image as background:
        // document.body.style.backgroundImage = "url('path/to/your-image.jpg')";
        // document.body.style.backgroundSize = 'cover';
        // document.body.style.backgroundPosition = 'center center';
        // document.body.style.backgroundRepeat = 'no-repeat';
    }, []); // Empty dependency array means this will run only once when the component mounts

    return(
      <HomePage/>
  );
}

function HomePage() {
    const containerStyles = {
        margin: '0 auto',
        marginTop: '50px',
        maxWidth: '1000px', // Limits the max width for larger screens

        borderRadius: '8px',
    };

    return (
        <div style={containerStyles}>
            <RecipeSearchBar/>
            <HomeShoppingList/>
            <HomeStorage/>
        </div>
    );
}

function HomeShoppingList() {
  return (
      <div style={{padding: '10px', border: '1px solid #ccc', marginBottom: "40px", backgroundColor: "white", borderRadius: "20px"}}>
          <h2>Shopping List</h2>
          {generateIngredientCards(ingredients)}
      </div>
  );
}

function RecipeSearchBar() {
    return (
        <div style={{padding: '10px', border: '1px solid #ccc', marginBottom: "40px", backgroundColor: "white", borderRadius: "20px"}}>
            <input
                type="text"
                placeholder="Search for items..."
                style={{width: '95%', margin:"0 auto", padding: '8px' }}
        />
      </div>
  );
}

function HomeStorage(){
  return (
      <div style={{padding: '10px',border: '1px solid #ccc', backgroundColor: "white", borderRadius: "20px"}}>
        <h2>My Storage</h2>
        <ul>
          <li>Rice</li>
          <li>Pasta</li>
          <li>Flour</li>
          {/* Add more items as needed */}
        </ul>
      </div>
  );
}

const IngredientCard = ({ name, quantity }) => {
    const cardStyles = {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '10px 20px',
        border: '1px solid #ccc',
        borderRadius: '8px',
        backgroundColor: '#f9f9f9',
        marginBottom: '10px', // Spacing for multiple cards
        width: '20%',
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
    };

    const nameStyles = {
        fontWeight: 'bold',
        fontSize: '16px',
        color: '#333',
    };

    const quantityStyles = {
        fontSize: '16px',
        color: '#666',
    };

    return (
        <div style={cardStyles}>
            <span style={nameStyles}>{name}</span>
            <span style={quantityStyles}>{quantity}</span>
        </div>
    );
};


export default App;

