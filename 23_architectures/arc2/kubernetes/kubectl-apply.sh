!/bin/bash
# Files are ordered in proper order with needed wait for the dependent custom resource definitions to get initialized.
# Usage: bash kubectl-apply.sh


usage(){
 cat << EOF

 Usage: $0 -f
    Apply all 
[OR]
 Usage: $0 -p
    Patch only aodb, flight and weather

EOF
exit 0
}

logSummary() {
    echo ""
    echo "#####################################################"
    echo "Please find the below useful endpoints,"
    echo "Gateway       - http://aodb.aodb.192.168.50.240.nip.io"
    echo "Tracing       - http://tracing.istio-system.192.168.50.240.nip.io"
    echo "Grafana       - http://grafana.istio-system.192.168.50.240.nip.io"
    echo "Kiali         - http://kiali.istio-system.192.168.50.240.nip.io"
    echo "Prometheus    - http://prometheus.istio-system.192.168.50.240.nip.io"
    echo "#####################################################"
}

default() {
    kubectl apply -f namespace.yml
    kubectl label namespace aodb istio-injection=enabled --overwrite=true
    kubectl apply -f aodb/
    kubectl apply -f flight/
    kubectl apply -f weather/

    kubectl apply -f istio/
    logSummary
}


patch() {
    kubectl -n aodb patch deployment aodb -p "{\"spec\":{\"template\":{\"metadata\":{\"labels\":{\"date\":\"`date +'%s'`\"}}}}}"
    kubectl -n aodb patch deployment weather -p "{\"spec\":{\"template\":{\"metadata\":{\"labels\":{\"date\":\"`date +'%s'`\"}}}}}"
    kubectl -n aodb patch deployment flight -p "{\"spec\":{\"template\":{\"metadata\":{\"labels\":{\"date\":\"`date +'%s'`\"}}}}}"
}

[[ "$@" =~ ^-[fp]{1}$ ]]  || usage;

while getopts ":fp" opt; do
    case ${opt} in
    f ) echo "Applying default \`kubectl apply -f\`"; default ;;
    p ) echo "Applying patch to aodb, flight and weather "; patch ;;
    \? | * ) usage ;;
    esac
done


