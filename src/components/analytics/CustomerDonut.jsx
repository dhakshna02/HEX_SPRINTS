import axios from "axios";
import { useEffect, useState } from "react";
import { Chart } from 'primereact/chart';
import "primereact/resources/themes/lara-light-blue/theme.css";  // theme
import "primereact/resources/primereact.min.css";               // core
import "primeicons/primeicons.css";                             // icons

const FinanceDonut = () => {

    const [chartData, setChartData] = useState(null);
    const [chartOptions, setChartOptions] = useState({});

    useEffect(() => {

        const getStats = async () => {
            try {

                const response = await axios.get(
                    'http://localhost:8081/api/transcation/inflow-outflow',
                    {
                        headers: {
                            'Authorization': 'Bearer ' + localStorage.getItem('Token')
                        }
                    }
                );

                console.log("API RESPONSE:", response.data);

                const apiData = response.data;

                const documentStyle = getComputedStyle(document.documentElement);

                // 🔹 Normalize values (important for doughnut visibility)
                const total =
                    Number(apiData.inFlow || 0) +
                    Number(apiData.outFlow || 0) +
                    Number(apiData.totalBalance || 0);

                const data = {
                    labels: ['Inflow', 'Outflow', 'Balance'],
                    datasets: [
                        {
                            data: total > 0 ? [
                                (apiData.inFlow / total) * 100,
                                (apiData.outFlow / total) * 100,
                                (apiData.totalBalance / total) * 100
                            ] : [0, 0, 0],
                            backgroundColor: [
                                documentStyle.getPropertyValue('--green-500'),
                                documentStyle.getPropertyValue('--red-500'),
                                documentStyle.getPropertyValue('--blue-500')
                            ],
                            hoverBackgroundColor: [
                                documentStyle.getPropertyValue('--green-400'),
                                documentStyle.getPropertyValue('--red-400'),
                                documentStyle.getPropertyValue('--blue-400')
                            ]
                        }
                    ]
                };

                const options = {
                    cutout: '60%',
                    plugins: {
                        legend: {
                            position: 'bottom'
                        }
                    }
                };

                setChartData(data);
                setChartOptions(options);

            } catch (error) {
                console.error("Error fetching stats:", error);
            }
        };

        getStats();

    }, []);

    // 🔹 Loading state
    if (!chartData) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            
                <Chart
                    type="doughnut"
                    data={chartData}
                    options={chartOptions}
                    className="w-full md:w-30rem"
                />
        
        </div>
    );
};

export default FinanceDonut;