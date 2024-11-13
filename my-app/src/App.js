import logo from './logo.svg';
import './App.css';
import './CSS/home-shopping-list.css'

function App() {
  return(
      <HomePage/>
  );
}
function HomePage() {
    const containerStyles = {
        width: '80%',
        marginTop: '50px',
        backgroundColor: 'white',
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
        borderRadius: '8px',
    };

    return (
        <div style={containerStyles}>
            <RecipeSearchBar/>
            <HomeShoppingList />
            <HomeStorage/>
        </div>
    );
}

function HomeShoppingList() {
  return (
      <div style={{padding: '10px', marginTop: '30px', borderBottom: '1px solid #ccc'}}>
          <h2>Previous Shopping List</h2>
          <ul>
              <li>Apples</li>
              <li>Bananas</li>
              <li>Bread</li>
              {/* Add more items as needed */}
          </ul>
      </div>
  );
}

function RecipeSearchBar() {
    return (
        <div style={{padding: '10px', borderBottom: '1px solid #ccc'}}>
            <input
                type="text"
                placeholder="Search for items..."
                style={{width: '100%', padding: '8px' }}
        />
      </div>
  );
}

function HomeStorage(){
  return (
      <div style={{padding: '10px'}}>
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

function IngredientCard() {
  return (
      <div></div>
  )
}

export default App;
