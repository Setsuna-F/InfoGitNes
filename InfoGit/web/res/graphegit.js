/***********************
 *  CUSTOM TEMPLATES   *
 ***********************/

var myTemplateConfig = {
  colors: ["#F00", "#0F0", "#00F"], // branches colors, 1 per column
  branch: {
    lineWidth: 8,
    spacingX: 50
  },
  commit: {
    spacingY: -80,
    dot: {
      size: 12
    },
    message: {
      displayAuthor: true,
      displayBranch: false,
      displayHash: false,
      font: "normal 12pt Arial"
    },
    shouldDisplayTooltipsInCompactMode: false, // default = true
    tooltipHTMLFormatter: function (commit) {
      return "<b>" + commit.sha1 + "</b>" + ": " + commit.message;
    }
  }
};
var myTemplate = new GitGraph.Template(myTemplateConfig);
