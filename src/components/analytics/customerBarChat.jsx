import axios from "axios";
import { useEffect, useState } from "react";
import { Chart } from 'primereact/chart';

const MonthlyComparisonChart = () => {

    const [chartData, setChartData] = useState(null);
    const [chartOptions, setChartOptions] = useState({});

    useEffect(() => {

        const getStats = async () => {

            const response = await axios.get(
                'http://localhost:8081/api/transcation/inflow-outflow',
                {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('Token')
                    }
                }
            );

            const apiData = response.data;

            const documentStyle = getComputedStyle(document.documentElement);

            const data = {
                labels: ['This Month', 'Last Month'],
                datasets: [
                    {
                        label: 'Inflow',
                        data: [
                            Number(apiData.inFlow || 0),
                            Number(apiData.inflowLatMonth || 0)
                        ],
                        backgroundColor: documentStyle.getPropertyValue('--green-500')
                    },
                    {
                        label: 'Outflow',
                        data: [
                            Number(apiData.outFlow || 0),
                            Number(apiData.outFlowLastMonth || 0)
                        ],
                        backgroundColor: documentStyle.getPropertyValue('--red-500')
                    }
                ]
            };

            const options = {
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            };

            setChartData(data);
            setChartOptions(options);
        };

        getStats();

    }, []);

    if (!chartData) return <div>Loading...</div>;

    return (
        
          <Chart type="bar" data={chartData} options={chartOptions} />

    );
};

export default MonthlyComparisonChart;
