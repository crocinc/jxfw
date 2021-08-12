package ru.croc.ctp.jxfw.reporting.xslfo.renderer.word.processing.tree.element.pagesequence.block;

import ru.croc.ctp.jxfw.reporting.xslfo.renderer.core.common.AreaType;
import ru.croc.ctp.jxfw.reporting.xslfo.renderer.word.processing.tree.element.root.GenericArea;

import java.util.Map;

/**
 * Класс, инкапсулирующий обработку элемента fo:page-number.
 * Created by vsavenkov on 06.07.2017. Import from $/Крок.R-and-D.ReportRenderers/Sources/Repo-1_1.
 */
public class PageNumberCitationArea extends GenericArea {

    /**
     * Инициализирующий конструктор.
     * @param parentArea    - Ссылка на родительскую область
     * @param attributeList - Список атрибутов
     */
    public PageNumberCitationArea(GenericArea parentArea, Map<String, String> attributeList) {
        super(AreaType.PAGE_NUMBER_CITATION, parentArea, attributeList);
    }
}
