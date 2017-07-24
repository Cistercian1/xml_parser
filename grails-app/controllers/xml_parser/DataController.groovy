package xml_parser

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.xml.sax.SAXParseException

/**
 * Контроллер для работы с импортом данных
 */
class DataController {

    def productService

    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    def index() {
        render(view: 'data')
    }

    /**
     * Функция импорта xml-файла
     * @return view
     */
    def importXml() {
        logger.debug("importXML()")

        def sourceXml = request.getFile('sourceXml')

        if (sourceXml.empty) {
            logger.info("Импортируемый файл не найден")

            flash.message = message(code: 'data.import.filenotfound')
            render(view: 'data')
            return
        }

        try {
            def startTime = System.currentTimeMillis()

            def xmlContent = productService.getXmlContent(sourceXml.getInputStream())
            flash.message = productService.importProductsXml(xmlContent)

            def time = System.currentTimeMillis() - startTime
            logger.info("Время обработки файла: ${time} ms")

            flash.message = "${flash.message}. Время обработки файла: ${time} ms."

        } catch (SAXParseException e) {
            logger.error("Ошибка парсинга файла: нарушена структура xml-файла. ${e.getMessage()}")
            flash.message = message(code: 'data.import.exceptionparsing')
        } finally {
            if (flash.message != null) {
                render(view: 'data')
                return
            }
        }

        respond flash.message, view: 'data'
    }
}
