parser: function (data) {
    var $ = cheerio.load(data);
    var objetos = [];
    var tableObjetos = $('table').find('tr');
    $(tableObjetos).map(function (key, objeto) {
        objeto = $(objeto).children('td').map(function (key, field) {
            return $(field).text();
        }).toArray();
        if (objeto[0]) {
            var rastreio = {
                codigo: null,
                situacao: null,
                local: null,
                data: null
            };
            rastreio.codigo = objeto[0].trim();
            if (objeto[2]) {
                rastreio.situacao = objeto[1];
                rastreio.local = objeto[2].substr(11, objeto[2].length).trim();
                rastreio.data = objeto[2].substr(0, 10).trim();
            } else {
                rastreio.situacao = 'Objeto ainda não consta no sistema'
            }

            objetos["return"] = rastreio;
        }
    });
    return Object.assign({}, objetos["return"]);
}