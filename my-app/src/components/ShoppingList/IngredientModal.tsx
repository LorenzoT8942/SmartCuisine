import React, { useState } from 'react';
import axios from 'axios';
import '../../CSS/ingredient-modal.css';

interface IngredientDTO {
    ingredientId: number;
    quantity: number;
}

interface Ingredient {
    id: number;
    name: string;
    quantity: number;
}

// interface SelectedIngredient {
//     id: number;
//     ingredient: IngredientDTO;
//     quantity: number;
// }

interface IngredientModalProps {
    onClose: () => void;
    onAdd: () => void;
    listName: string | undefined;
}

interface AddIngredientRequestDTO {
    username: string;
    shoppingListName: string;
    ingredients: IngredientDTO[];
}

const IngredientModal: React.FC<IngredientModalProps> = ({ onClose, onAdd, listName }) => {
    const [searchTerm, setSearchTerm] = useState('');
    const [searchResults, setSearchResults] = useState<Ingredient[]>([]);
    const [selectedIngredients, setSelectedIngredients] = useState<Ingredient[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [shoppingListName, setShoppingListName] = useState<string | undefined>(listName);
    const token = localStorage.getItem('authToken');

    const handleSearch = async () => {
        if (token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get(`http://localhost:3004/recipes-ingredients/ingredients/search-by-name?name=${searchTerm}`, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            });
            setSearchResults(response.data);
            console.log(response.data);
        } catch (err) {
            setError('Error fetching ingredients');
        } finally {
            setLoading(false);
        }
    };

    const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            handleSearch();
        }
    };

    const handleAddIngredient = (ingredient: Ingredient) => {
        if (selectedIngredients.some(selected => selected.id === ingredient.id)) {
            alert('This ingredient has already been added.');
            return;
        }
        const quantity = prompt(`Enter quantity in grams for ${ingredient.name}:`);
        if (quantity && !isNaN(Number(quantity))) {
            ingredient.quantity = Number(quantity);
            setSelectedIngredients([...selectedIngredients, ingredient]);
        } else {
            alert('Please enter a valid number for the quantity.');
        }
    };

    const handleRemoveIngredient = (index: number) => {
        const updatedIngredients = selectedIngredients.filter((_, i) => i !== index);
        setSelectedIngredients(updatedIngredients);
    };

    const handleConfirm = async () => {
        if (token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        if (listName === undefined) throw new Error("listName is undefined");
        const addIngredientRequest: AddIngredientRequestDTO = {
            username: "username",
            shoppingListName: listName, // Replace with actual shopping list name
            ingredients: selectedIngredients.map(selected => ({
                ingredientId: selected.id,
                quantity: selected.quantity
            }))
        };
        try {
            await axios.post('http://localhost:3002/shopping-lists/add-ingredient', addIngredientRequest, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`,
                    'Content-Type': 'application/json'
                }
            });
            alert('Ingredients added successfully');
            onAdd()
            onClose();
        } catch (err) {
            alert('Error adding ingredients');
            console.error(err);
        }
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="close-button" onClick={onClose}>X</button>
                <h2>Search Ingredient</h2>
                <form onSubmit={(e) => e.preventDefault()}>
                    <input
                        type="text"
                        className="search-bar"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        onKeyPress={handleKeyPress}
                        placeholder="Search for an ingredient"
                    />
                </form>
                {loading && <p>Loading...</p>}
                {error && <p>{error}</p>}
                <div className="ingredient-selection">
                    <ul className="search-results">
                        {searchResults.length > 0 ? (
                            searchResults.map((ingredient) => (
                                <li key={ingredient.id} onClick={() => handleAddIngredient(ingredient)}>
                                    {ingredient.name}
                                </li>
                            ))
                        ) : (
                            <p>No ingredients found</p>
                        )}
                    </ul>
                    <div className="vertical-line"></div>
                    <div className="selected-ingredients">
                        <h3>Selected Ingredients</h3>
                        <ul>
                            {selectedIngredients.map((selected, index) => (
                                <li key={index}>
                                    {selected.name}: {selected.quantity} grams
                                    <button className="remove-button" onClick={() => handleRemoveIngredient(index)}>X</button>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
                <button className="confirm-button" onClick={handleConfirm}>Confirm</button>
                <button className="cancel-button" onClick={onClose}>Cancel</button>
            </div>
        </div>
    );
};

export default IngredientModal;