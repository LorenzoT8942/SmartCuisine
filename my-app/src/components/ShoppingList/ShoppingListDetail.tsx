import axios from 'axios';
import React from 'react';
import { useParams } from 'react-router-dom';

interface Ingredient {
    id: number;
    name: String;
    quantity: number;
    unit: String;
}

interface ShoppingList {
    id: number;
    name: String;
    ingredients: Array<Ingredient>;
}

const ShoppingListDetails = () => {
    const { id } = useParams<{ id: string }>();
    const [shoppingList, setShoppingList] = React.useState<ShoppingList | null>(null);
    const [loading, setLoading] = React.useState(true);
    const [error, setError] = React.useState<string | null>(null);
    const token = localStorage.getItem('authToken');

    React.useEffect(() => {
        const fetchShoppingList = async () => {
            if (token == null) throw new Error("the token is null");
            const parsedData = JSON.parse(token);
            const tokenParsed = parsedData.token;
            try {
                setLoading(true);
                const response = await axios.get(`http://localhost:3002/shopping-lists/${id}`, {
                    headers: {
                        Authorization: `Bearer ${tokenParsed}`
                    }
                });
                setShoppingList(response.data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchShoppingList();
    }, [id, token]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;

    return (
        <div>
            <h1>{shoppingList?.name}</h1>
            <ul>
                {shoppingList?.ingredients.map((ingredient) => (
                    <li key={ingredient.id}>
                        {ingredient.name}: {ingredient.quantity} {ingredient.unit}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ShoppingListDetails;